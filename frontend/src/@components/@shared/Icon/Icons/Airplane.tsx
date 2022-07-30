import { SVGProps } from 'react';

const Airplane = (props: SVGProps<SVGSVGElement>) => {
  return (
    <svg
      width='21'
      height='24'
      viewBox='0 0 21 24'
      fill='none'
      xmlns='http://www.w3.org/2000/svg'
      {...props}
    >
      <g clipPath='url(#clip0_496_2259)'>
        <path
          d='M2.87164 19.7673L17.6098 12.5193C18.2939 12.1801 18.2939 11.0755 17.6098 10.7363L2.87164 3.48825C2.31421 3.20725 1.69765 3.68205 1.69765 4.37004L1.68921 8.83709C1.68921 9.32159 2.00171 9.73825 2.42401 9.79639L14.3581 11.6278L2.42401 13.4495C2.00171 13.5173 1.68921 13.934 1.68921 14.4185L1.69765 18.8855C1.69765 19.5735 2.31421 20.0483 2.87164 19.7673Z'
          fill='white'
        />
      </g>
      <defs>
        <clipPath id='clip0_496_2259'>
          <rect width='20.2703' height='23.2558' fill='white' />
        </clipPath>
      </defs>
    </svg>
  );
};

export default Airplane;
