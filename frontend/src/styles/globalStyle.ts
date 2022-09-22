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
    font-family: '';
    src: local(''), local('Pretendard'), url('Pretendard.woff2') format('woff2'),
      url('Pretendard.woff') format('woff');
    font-weight: normal;
    font-style: normal;
    font-display: swap;
  }

  @font-face {
    font-family: '';
    src: local(''), local('Pretendard'), url('Pretendard.woff2') format('woff2'),
      url('Pretendard.woff') format('woff');
    font-weight: lighter;
    font-style: normal;
    font-display: swap;
  }

  @font-face {
    font-family: '';
    src: local(''), local('Pretendard'), url('Pretendard.woff2') format('woff2'),
      url('Pretendard.woff') format('woff');
    font-weight: 600;
    font-style: normal;
    font-display: swap;
  }

  @font-face {
    font-family: '';
    src: local(''), local('Pretendard'), url('Pretendard.woff2') format('woff2'),
      url('Pretendard.woff') format('woff');
    font-weight: bold;
    font-style: normal;
    font-display: swap;
  }

  @font-face {
    font-family: '';
    src: local(''), local('Pretendard'), url('Pretendard.woff2') format('woff2'),
      url('Pretendard.woff') format('woff');
    font-weight: 900;
    font-style: normal;
    font-display: swap;
  }

  @font-face {
    font-family: '';
    src: local(''), local('BMHANNAProOTF'), url('BMHANNAProOTF.woff2') format('woff2'),
      url('BMHANNAProOTF.woff') format('woff');
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
`;

export default globalStyle;
