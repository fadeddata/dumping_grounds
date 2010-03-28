#include <stdio.h>   /* Standard input/output definitions */
#include <string.h>  /* String function definitions */
#include <unistd.h>  /* UNIX standard function definitions */
#include <fcntl.h>   /* File control definitions */
#include <errno.h>   /* Error number definitions */
#include <termios.h> /* POSIX terminal control definitions */

/*
 * 'open_port()' - Open serial port 1.
 *
 * Returns the file descriptor on success or -1 on error.
 */

int open_port(void)
{
  
  int fd; /* File descriptor for the port */

  fd = open("/dev/tty.USA19H1b1P1.1", O_RDWR | O_NOCTTY | O_NDELAY);
  if (fd == -1)
    {
      /*
       * Could not open the port.
       */
      
      perror("open_port: Unable to open /dev/ttyS0 - ");
    }
  else
    fcntl(fd, F_SETFL, 0);
  
  return (fd);
}

main() {
  int fd = open_port();
  unsigned char buf[20];
  size_t nbytes;
  ssize_t bytes_read;
  int i;

  struct termios options;
  
  /*
   * Get the current options for the port...
   */
  
  tcgetattr(fd, &options);
  
  /*
   * Enable the receiver and set local mode...
   */
  
  options.c_cflag &= (CLOCAL | CREAD);
  options.c_cflag &= ~PARENB;
  options.c_cflag &= ~CSTOPB;
  options.c_cflag &= ~CSIZE;
  options.c_cflag |= CS8;
  
  /*
   * Set the baud rates to 19200...
   */
  
  cfsetispeed(&options, B115200);
  cfsetospeed(&options, B115200);
  
  /*
   * Set the new options for the port...
   */
  
  tcsetattr(fd, TCSANOW, &options);

  fcntl(fd, F_SETFL, 0);
  
  while(1) {
    nbytes = sizeof(buf);
    bytes_read = read(fd, buf, nbytes);

    for (i = 0; i < nbytes; i++)
      {
	printf("%d ", buf[i]);
      }
  }

  close(fd);
}
