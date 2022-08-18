import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;

  display: flex;
  flex-direction: column;
  gap: 24px;

  padding: 10px 0;

  ${({ theme }) => css`
    color: ${theme.colors.drak_grey_200};
  `}
`;

export const CouponInfoContainer = styled.div`
  display: flex;

  gap: 10px;
`;

export const ProfileImageContainer = styled.div`
  width: 20%;
  display: flex;
  justify-content: center;
  align-items: center;

  & > img {
    width: 50px;

    border-radius: 50%;
  }
`;

export const InformationContainer = styled.div`
  width: 75%;

  display: flex;
  flex-direction: column;

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

  word-break: break-all;
`;
