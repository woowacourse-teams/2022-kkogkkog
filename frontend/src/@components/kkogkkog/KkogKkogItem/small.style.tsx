import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div<{ hasCursor?: boolean }>`
  width: 120px;

  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  box-shadow: 4px 4px 20px 0px #160f3514;
  border-radius: 20px;

  padding: 12px;

  gap: 10px;

  ${({ hasCursor = true }) =>
    hasCursor &&
    css`
      cursor: pointer;
    `}
`;

export const StatusContainer = styled.div<{
  couponStatus: 'REQUESTED' | 'READY' | 'ACCEPTED' | 'FINISHED';
}>`
  padding: 3px 5px;
  border-radius: 20px;

  font-size: 12px;

  ${({ theme }) => css`
    color: ${theme.colors.white_100};
  `}

  ${({ theme, couponStatus }) => {
    if (couponStatus === 'REQUESTED') {
      return css`
        background-color: ${theme.colors.primary_500};
      `;
    }

    if (couponStatus === 'ACCEPTED') {
      return css`
        background-color: ${theme.colors.green_500};
      `;
    }

    if (couponStatus === 'READY') {
      return css`
        background-color: ${theme.colors.primary_300};
      `;
    }

    return css`
      background-color: ${theme.colors.grey_300};
    `;
  }}
`;

export const TextContainer = styled.div`
  font-weight: 600;
`;

export const Preposition = styled.span`
  font-family: BMHANNAProOTF;

  ${({ theme }) => css`
    color: ${theme.colors.primary_400};
  `}
`;
