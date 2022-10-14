import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { CSSProperties } from 'react';

type PlaceholderProps = Pick<CSSProperties, 'width' | 'height' | 'aspectRatio' | 'maxWidth'>;

const Placeholder = styled.div<PlaceholderProps>`
  border-radius: 20px;
  background-image: linear-gradient(90deg, #e0e0e0 0px, #ededed 30px, #e0e0e0 60px);
  animation: refresh 2s infinite ease-out;

  @keyframes refresh {
    0% {
      background-position: calc(-100px);
    }
    40%,
    100% {
      background-position: 320px;
    }
  }

  ${({ width = '100%', height = '100%', aspectRatio, maxWidth }) => css`
    width: ${width};
    max-width: ${maxWidth};
    height: ${height};

    aspect-ratio: ${aspectRatio};
  `}
`;

export default Placeholder;
