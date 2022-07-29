import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  position: fixed;

  bottom: 20px;

  width: 100%;

  display: flex;
  justify-content: center;
`;

export const Container = styled.div<{ isError: boolean }>`
  cursor: pointer;

  max-width: 80%;

  padding: 15px 10px;

  border-radius: 20px;

  ${({ theme, isError }) => css`
    background-color: ${isError ? theme.colors.red_800 : theme.colors.green_500};
    color: ${theme.colors.white_100};
  `}
  -webkit-animation: slide-in-blurred-bottom 1.5s cubic-bezier(0.230, 1.000, 0.320, 1.000) 2 alternate both;
  animation: slide-in-blurred-bottom 1.5s cubic-bezier(0.23, 1, 0.32, 1) 2 alternate both;

  @-webkit-keyframes slide-in-blurred-bottom {
    0% {
      -webkit-transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      -webkit-transform-origin: 50% 100%;
      transform-origin: 50% 100%;
      -webkit-filter: blur(40px);
      filter: blur(40px);
      opacity: 0;
    }

    60% {
      -webkit-transform: translateY(0) scaleY(1) scaleX(1);
      transform: translateY(0) scaleY(1) scaleX(1);
      -webkit-transform-origin: 50% 50%;
      transform-origin: 50% 50%;
      -webkit-filter: blur(0);
      filter: blur(0);
      opacity: 1;
    }
  }
  @keyframes slide-in-blurred-bottom {
    0% {
      -webkit-transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      -webkit-transform-origin: 50% 100%;
      transform-origin: 50% 100%;
      -webkit-filter: blur(40px);
      filter: blur(40px);
      opacity: 0;
    }
    50% {
      -webkit-transform: translateY(0) scaleY(1) scaleX(1);
      transform: translateY(0) scaleY(1) scaleX(1);
      -webkit-transform-origin: 50% 50%;
      transform-origin: 50% 50%;
      -webkit-filter: blur(0);
      filter: blur(0);
      opacity: 1;
    }
  }
`;
