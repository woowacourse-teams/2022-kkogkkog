import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ShowUpRoot = styled.div`
  position: fixed;

  bottom: 20px;

  width: 100%;

  display: flex;
  justify-content: center;

  -webkit-animation: slide-in-blurred-bottom 1.5s cubic-bezier(0.23, 1, 0.32, 1) 1 alternate both;
  animation: slide-in-blurred-bottom 1.5s cubic-bezier(0.23, 1, 0.32, 1) 1 alternate both;

  @keyframes slide-in-blurred-bottom {
    0% {
      -webkit-transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      -webkit-transform-origin: 50% 100%;
      transform-origin: 50% 100%;
      -webkit-filter: blur(40px);
      filter: blur(40px);
      display: none;
    }
    50% {
      -webkit-transform: translateY(0) scaleY(1) scaleX(1);
      transform: translateY(0) scaleY(1) scaleX(1);
      -webkit-transform-origin: 50% 50%;
      transform-origin: 50% 50%;
      -webkit-filter: blur(0);
      filter: blur(0);
      display: flex;
    }
  }
`;

export const ShowDownRoot = styled.div`
  position: fixed;

  bottom: 20px;

  width: 100%;

  display: flex;
  justify-content: center;

  z-index: ${({ theme }) => theme.layers.toast};

  -webkit-animation: slide-in-blurred-bottom 1s cubic-bezier(0.23, 1, 0.32, 1) 1 alternate-reverse
    both;
  animation: slide-in-blurred-bottom 1s cubic-bezier(0.23, 1, 0.32, 1) 1 alternate-reverse both;

  @keyframes slide-in-blurred-bottom {
    0% {
      -webkit-transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      transform: translateY(1000px) scaleY(2.5) scaleX(0.2);
      -webkit-transform-origin: 50% 100%;
      transform-origin: 50% 100%;
      -webkit-filter: blur(40px);
      filter: blur(40px);
      display: none;
    }
    50% {
      -webkit-transform: translateY(0) scaleY(1) scaleX(1);
      transform: translateY(0) scaleY(1) scaleX(1);
      -webkit-transform-origin: 50% 50%;
      transform-origin: 50% 50%;
      -webkit-filter: blur(0);
      filter: blur(0);
      display: flex;
    }
  }
`;

export const ToastContainer = styled.div<{ isError: boolean }>`
  width: 300px;

  padding-left: 5px;
  border-radius: 10px;

  ${({ theme, isError }) => css`
    background-color: ${isError ? theme.colors.red_800 : theme.colors.green_500};
    color: ${isError ? theme.colors.red_800 : theme.colors.green_500};
    font-weight: 600;
    box-shadow: ${theme.shadow.type_3};

    & > h2 {
      background-color: ${isError ? '#FFC7C2' : '#D6FFCC'};
    }
  `}
`;

export const ToastMessage = styled.h2`
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  padding: 20px 10px 20px 20px;

  border-radius: 0 10px 10px 0;
`;
