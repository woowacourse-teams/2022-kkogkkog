import { css } from '@emotion/react';

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

  button {
    cursor: pointer;
    border: 0;
    background-color: inherit;
  }

  @font-face {
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: lighter;
    src: url('/assets/font/Pretendard-Light.woff') format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: normal;
    src: url('/assets/font/Pretendard-Regular.woff') format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: 600;
    src: url('/assets/font/Pretendard-SemiBold.woff') format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: bold;
    src: url('/assets/font/Pretendard-Bold.woff') format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: 900;
    src: url('/assets/font/Pretendard-ExtraBold.woff') format('woff');
  }

  @font-face {
    font-family: 'BMHANNAProOTF';
    font-style: normal;
    font-weight: bold;
    src: url('/assets/font/BMHANNAProOTF.otf') format('woff');
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
`;

export default globalStyle;
