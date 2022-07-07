import { css } from '@emotion/react';
import React from 'react';
import ReactDOM from 'react-dom';

import { Dimmed } from '@/@components/@shared/Dimmed';

function Loading({ children }: React.PropsWithChildren) {
  return ReactDOM.createPortal(
    <Dimmed>
      <div
        css={css`
          font-size: 40px;

          animation: rotateY 1.5s infinite;

          @keyframes rotateY {
            0% {
              transform: rotateX(0);
            }

            60% {
              transform: rotateX(360deg);
            }

            100% {
              transform: rotateX(360deg);
            }
          }
        `}
      >
        {children}
      </div>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

export default Loading;
