import React, { SVGProps } from 'react';

const Notification = (props: SVGProps<SVGSVGElement>) => {
  return (
    <svg
      width='24'
      height='26'
      viewBox='0 0 24 26'
      fill='none'
      xmlns='http://www.w3.org/2000/svg'
      {...props}
    >
      <path
        d='M12 1.42859V4.09892M4 13.8901C4 9.43958 5 4.09892 12 4.09892C19 4.09892 20 9.43958 20 13.8901C20 18.3407 23 21.011 23 21.011H1C1 21.011 4 18.3407 4 13.8901ZM16 21.011C16 21.011 16 24.5714 12 24.5714C8 24.5714 8 21.011 8 21.011H16Z'
        stroke='#FF7020'
        strokeWidth='2'
        strokeLinecap='round'
        strokeLinejoin='round'
      />
    </svg>
  );
};

export default Notification;
