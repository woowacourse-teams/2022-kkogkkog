import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { COUPON_STATUS } from '@/types/coupon/client';

export const Root = styled.div`
  width: 100%;

  max-width: 340px;
  min-width: 280px;

  height: 120px;

  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;

  border-radius: 20px;

  box-shadow: 0 0 20px rgba(0, 0, 0, 0.12);

  ${({ theme }) => css`
    background-color: ${theme.colors.white_100};
  `}
`;

export const Top = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const Message = styled.p`
  font-size: 12px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: left;
`;

export const MeetingDate = styled.span<{ couponStatus: COUPON_STATUS }>`
  font-size: 14px;

  ${({ theme, couponStatus }) => {
    if (couponStatus === 'REQUESTED') {
      return css`
        color: ${theme.colors.primary_500};
      `;
    }

    if (couponStatus === 'ACCEPTED') {
      return css`
        color: ${theme.colors.green_500};
      `;
    }

    if (couponStatus === 'FINISHED') {
      return css`
        color: ${theme.colors.light_grey_100};
      `;
    }
  }}
`;

export const Member = styled.p`
  font-weight: 600;
`;

export const TextContainer = styled.div`
  flex: 1;
  overflow: hidden;

  height: 100%;
  white-space: nowrap;

  display: flex;
  flex-direction: column;
  justify-content: space-between;

  font-size: 14px;

  padding: 15px 15px 15px 0;

  ${({ theme }) => css`
    color: ${theme.colors.dark_grey_200};
  `}
`;

export const English = styled.span`
  font-family: BMHANNAProOTF;
  ${({ theme }) => css`
    color: ${theme.colors.primary_400};
  `}
`;

export const TypeText = styled.span`
  text-decoration: underline 2px;
`;

export const CouponPropertyContainer = styled.div`
  width: 30%;
  height: 100%;

  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  border-radius: 20px 0 0 20px;
  padding: 16px 0;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_300_opacity};
  `}
`;

export const ImageContainer = styled(CouponPropertyContainer)`
  justify-content: center;
`;

export const ImageInner = styled.div`
  height: 58px;
  width: 58px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 12px;

  ${({ theme }) => css`
    background-color: ${theme.colors.white_100};
  `}

  & > img {
    border-radius: 20px;
  }
`;

export const Hashtag = styled.div`
  width: 67px;
  height: 24px;
  line-height: 24px;
  font-family: 'BMHANNAProOTF';
  font-size: 12px;
  text-align: center;

  border-radius: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400};
    color: ${theme.colors.white_100};
  `}
`;

export const LinkButtonContainer = styled.div`
  font-size: 32px;

  text-align: center;
  width: 100%;
`;

export const LinkButtonText = styled.div`
  font-size: 14px;
`;
