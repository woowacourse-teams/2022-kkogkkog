import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { CSSProperties } from 'react';

const Position = styled.div<Pick<CSSProperties, 'position' | 'top' | 'bottom' | 'left' | 'right'>>`
  ${({ position = 'relative', top, bottom, left, right }) => css`
    position: ${position};
    top: ${top};
    bottom: ${bottom};
    left: ${left};
    right: ${right};
  `}
`;

export default Position;
