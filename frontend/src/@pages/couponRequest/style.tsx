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
  flex: 1;
  padding: 30px 16px 40px;
  border-radius: 20px 20px 0 0;
  position: relative;
  top: -20px;
  background-color: white;

  ${({ theme }) => css`
    box-shadow: ${theme.shadow.top_1};
  `}
`;

export const SectionTitle = styled.div`
  font-weight: 600;
  margin-bottom: 30px;
  ${({ theme }) => css`
    color: ${theme.colors.drak_grey_200};
  `}
`;

export const Description = styled.div`
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 16px;
  ${({ theme }) => css`
    color: ${theme.colors.drak_grey_200};
  `}
`;

export const DateInput = styled.input`
  width: 100%;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 20px;

  ${({ theme }) => css`
    border: 1px solid ${theme.colors.grey_100};
  `}
`;

export const MessageTextarea = styled.textarea`
  width: 100%;
  height: 200px;
  border: none;
  padding: 16px;
  outline: none;
  resize: none;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_3};
  `}
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
