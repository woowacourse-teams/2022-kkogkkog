import type { Theme } from '@emotion/react';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  min-height: calc(var(--vh, 1vh) * 100);
`;

export const Top = styled.div`
  width: 100%;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400_opacity};
  `}
`;

export const ProfileImage = styled.img`
  border-radius: 50%;
  margin-bottom: 24px;
`;

export const SummaryMessage = styled.span`
  font-size: 16px;

  strong {
    font-weight: 600;
  }
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

export const SubSection = styled.section`
  margin-top: 40px;
`;

export const SubSectionTitle = styled.span`
  font-weight: 600;
  ${({ theme }) => css`
    color: ${theme.colors.drak_grey_200};
  `}
`;

export const CodeContainer = styled.div`
  min-height: 150px;
  margin-top: 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  font-size: 14px;
  border-radius: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_3};
  `}
`;

export const DescriptionContainer = styled.div`
  min-height: 100px;

  margin-top: 10px;

  font-size: 14px;
  border-radius: 20px;

  padding: 15px;

  line-height: 1.5;
  word-break: break-all;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_3};
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
  max-width: 414px;

  display: flex;

  left: 50%;
  transform: translateX(-50%);

  & > button + button {
    border-left: 1px solid ${theme.colors.grey_100};
  }
`;
