import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  position: relative;

  width: 100%;
  max-width: 340px;
  min-width: 280px;

  height: 120px;

  border-radius: 20px;
`;

export const Coupon = styled.div<{ hasCursor?: boolean }>`
  width: 100%;
  height: 100%;

  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;

  border-radius: 20px;

  box-shadow: 0 0 20px rgba(0, 0, 0, 0.12);

  ${({ theme }) => css`
    background-color: ${theme.colors.white_100};
  `}

  ${({ hasCursor = true }) =>
    hasCursor &&
    css`
      cursor: pointer;
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
    color: ${theme.colors.drak_grey_200};
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

export const CopyButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
`;
