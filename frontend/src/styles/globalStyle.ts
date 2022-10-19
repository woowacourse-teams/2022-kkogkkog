import { css } from '@emotion/react';

import BMHANNAProWoff from '@/assets/font/BMHANNAProOTF.woff';
import BMHANNAProWoff2 from '@/assets/font/BMHANNAProOTF.woff2';

const globalStyle = css`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font: inherit;
    color: inherit;
    flex-shrink: 0;
  }

  body {
    font-family: 'Pretendard';
    letter-spacing: -0.03px;
  }

  a {
    text-decoration: none;
    color: inherit;
  }

  ol,
  ul {
    list-style: none;
  }

  li {
    list-style: none;
  }

  button {
    cursor: pointer;
    border: 0;
    background-color: inherit;
  }

  @font-face {
    font-family: 'BMHANNAProOTF';
    src: local('BMHANNAProOTF'), url(${BMHANNAProWoff2}) format('woff2'),
      url(${BMHANNAProWoff}) format('woff');
    font-weight: bold;
    font-style: normal;
    font-display: swap;
  }

  @keyframes show-up {
    from {
      opacity: 0;
    }

    to {
      opacity: 0.99;
    }
  }

  @keyframes drop-down {
    0% {
      transform: scaleY(0);
    }
    80% {
      transform: scaleY(1.1);
    }
    100% {
      transform: scaleY(1);
    }
  }

  @media (max-width: 410px) {
    input {
      font-size: 16px !important;
    }
  }
  div {
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
  }

  div::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }

  input[type='number']::-webkit-outer-spin-button,
  input[type='number']::-webkit-inner-spin-button {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
  }
  input[type='number'] {
    -moz-appearance: textfield;
  }
`;

export default globalStyle;
