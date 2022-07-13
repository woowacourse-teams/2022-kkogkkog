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
    font-weight: bold;
    src: url('/assets/font/Pretendard-Bold.woff') format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: bolder;
    src: url('/assets/font/Pretendard-ExtraBolder.woff') format('woff');
  }
`;

export default globalStyle;
