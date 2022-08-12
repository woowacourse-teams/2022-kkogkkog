import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
`;

export const Top = styled.div`
  width: 100%;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_200};
  `}
`;

export const ProfileImage = styled.img`
  width: 51px;
  border-radius: 50%;
  margin-bottom: 24px;
`;

export const SummaryMessage = styled.span`
  font-size: 14px;
  font-weight: 600;
`;

export const Main = styled.main`
  height: calc(100% - 100px);
  padding: 30px 16px 40px;
  border-radius: 20px 20px 0 0;
  position: relative;
  top: -20px;
  background-color: white;

  ${({ theme }) => css`
    box-shadow: ${theme.shadow.top_1};
  `}
`;

export const CouponInner = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
`;

export const HistorySection = styled.section`
  margin-top: 40px;
`;

export const HistoryTitle = styled.span`
  font-weight: 600;
  ${({ theme }) => css`
    color: ${theme.colors.drak_grey_200};
  `}
`;

export const FinishButtonInner = styled.div`
  width: 100%;
  text-align: right;
  font-size: 12px;
  margin-top: 16px;

  ${({ theme }) => css`
    color: ${theme.colors.grey_200};
  `}

  & > button {
    text-decoration: underline;
  }
`;

export const ExtendedButton = css`
  height: 50px;
  border-radius: 0;
  flex: 1;
`;

export const ExtendedPosition = (theme: Theme) => css`
  width: 100%;
  display: flex;

  & > button + button {
    border-left: 1px solid ${theme.colors.grey_100};
  }
`;
