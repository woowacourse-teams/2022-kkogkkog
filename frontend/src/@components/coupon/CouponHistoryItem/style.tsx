import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  padding: 21px 19px;

  display: flex;
  flex-direction: column;
  gap: 24px;

  ${({ theme }) => css`
    color: ${theme.colors.drak_grey_200};
  `}
`;

export const CouponInfoContainer = styled.div`
  display: flex;

  gap: 18px;
`;

export const ProfileImageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  & > img {
    width: 50px;

    border-radius: 50%;
  }
`;

export const InformationContainer = styled.div`
  display: flex;
  flex-direction: column;

  justify-content: space-between;
`;

export const Contents = styled.div`
  font-size: 12px;
`;
export const Date = styled.div`
  font-size: 12px;

  ${({ theme }) => css`
    color: ${theme.colors.grey_200};
  `}
`;

export const Message = styled.p`
  font-size: 12px;
`;
