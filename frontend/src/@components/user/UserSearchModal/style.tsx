import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const UserSearchModal = (theme: Theme) => css`
  position: relative;
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 5px;
  right: 5px;

  border-radius: 20px;
`;
