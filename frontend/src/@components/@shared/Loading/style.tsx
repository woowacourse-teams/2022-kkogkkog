import styled from '@emotion/styled';

export const Root = styled.div`
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
`;
