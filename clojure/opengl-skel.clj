(import '(java.awt Frame)
	'(java.awt.event WindowListener WindowAdapter)
	'(javax.media.opengl GLCanvas GLEventListener GL)
	'(com.sun.opengl.util Animator ))

(defmacro gl-beg-end 
  [gl mode & body]
  (list 'do 
	(list '. 'gl 'glBegin (list '. 'GL mode))
	(cons 'do body)
	(list '. 'gl 'glEnd)))
  

(defn go []
  (let [frame (new Frame)
	gl-canvas (new GLCanvas)
	animator (new Animator gl-canvas)]
    (. gl-canvas 
       (addGLEventListener
	(proxy [GLEventListener] []
	  (display [#^javax.media.opengl.GLAutoDrawable drawable]
		   (let [gl (. drawable getGL)]
		     (doto gl
		       (glClearColor 0.0, 0.0, 0.0, 0.0)
		       (glClear (. GL GL_COLOR_BUFFER_BIT))
		       (glColor3f 1.0, 1.0, 1.0)
		       (glOrtho 0.0, 1.0, 0.0, 1.0, -1.0, 1.0))
		     (gl-beg-end gl GL_POLYGON
		       (doto gl
			 (glVertex3f 0.25, 0.25, 0.0)
			 (glVertex3f 0.75, 0.25, 0.0)
		      (glVertex3f 0.75, 0.75, 0.0)
		      (glVertex3f 0.25, 0.75, 0.0)))
		     (. gl glFlush)))
	  (displayChanged [drawable mode-changed device-changed])
	  (init [#^javax.media.opengl.GLAutoDrawable drawable]
		(let [gl (. drawable getGL)]
		  (doto gl
		    (glClearColor 0.0, 0.0, 0.0, 0.0)
		    (glClear (. GL GL_COLOR_BUFFER_BIT))
		    (glColor3f 1.0, 1.0, 1.0)
		    (glOrtho 0.0, 1.0, 0.0, 1.0, -1.0, 1.0))
		  (gl-beg-end gl GL_POLYGON
		    (doto gl
		      (glVertex3f 0.25, 0.25, 0.0)
		      (glVertex3f 0.75, 0.25, 0.0)
		      (glVertex3f 0.75, 0.75, 0.0)
		      (glVertex3f 0.25, 0.75, 0.0)))
		  (. gl glFlush)))
	  (reshape [#^javax.media.opengl.GLAutoDrawable drawable x y width height]
		   (let [gl (. drawable getGL)
			 h (/ height width)]
		     (doto gl
		       (glMatrixMode (. GL GL_PROJECTION))
		       (glLoadIdentity)
		       (glFrustum -1.0 1.0 (- h) h 5.0 60.0)
		       (glMatrixMode (. GL GL_MODELVIEW))
		       (glLoadIdentity)
		       (glTranslatef 0.0 0.0 -40.0))
		     (.. System out (println (str "GL_VENDOR: " (. gl glGetString (. GL GL_VENDOR)))))
		     (.. System out (println (str "GL_RENDERER: " (. gl glGetString (. GL GL_RENDERER)))))
		     (.. System out (println (str "GL_VERSION: " (. gl glGetString (. GL GL_VERSION))))))))))
    (. frame add gl-canvas)
    (. frame (setSize 300 300))
    (. frame
       (addWindowListener
	(proxy [WindowAdapter] []
	  (windowClosing [event]
			 (. (new Thread
				 (fn []
				   (. animator stop)
				   (. frame dispose))) start)))))
    (. frame show)
    (. animator start)))