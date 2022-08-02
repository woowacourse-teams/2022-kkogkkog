import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 24px;

  gap: 27px;
  padding-top: 27px;
`;

export const MeInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 24px;
`;

export const ProfileImage = styled.img`
  border-radius: 50%;
`;

export const NickName = styled.span`
  font-size: 18px;
  font-weight: 600;
`;

export const ButtonInner = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 20px;
  font-size: 12px;
  ${({ theme }) => css`
    color: ${theme.colors.grey_200};
  `}

  & > button {
    text-decoration: underline;
  }
`;
