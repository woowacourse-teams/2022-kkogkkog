import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;

  display: flex;
  flex-direction: column;
  gap: 16px;

  padding: 20px 0;

  ${({ theme }) => css`
    color: ${theme.colors.dark_grey_200};
  `}
`;

export const CouponInfoContainer = styled.div`
  display: flex;

  gap: 16px;
`;

export const ProfileImageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  & > img {
    border-radius: 50%;
  }
`;

export const InformationContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;

  justify-content: space-between;
`;

export const Contents = styled.div`
  font-size: 12px;

  word-break: break-all;
`;

export const Date = styled.div`
  font-size: 12px;

  ${({ theme }) => css`
    color: ${theme.colors.grey_200};
  `}
`;

export const Message = styled.p`
  font-size: 12px;
  /* 이미지 사이즈 + gap */
  padding-left: 66px;

  word-break: break-all;
`;
