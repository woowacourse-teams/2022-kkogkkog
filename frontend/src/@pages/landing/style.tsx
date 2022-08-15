import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  background-color: ${({ theme }) => theme.colors.white_100};
  border-radius: 4px;
`;

export const UnAuthorizedRoot = styled.div`
  min-height: calc(var(--vh, 1vh) * 100);
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400_opacity};
  `}
`;

export const UnAuthorizedContainer = styled.div`
  display: flex;

  flex-direction: column;
  justify-content: center;
  align-items: center;

  text-align: center;
  font-weight: 600;

  gap: 20px;
`;

export const CreateCouponContainer = styled.div`
  display: flex;

  flex-direction: column;
  justify-content: center;
  align-items: center;

  text-align: center;
  font-weight: 600;

  padding: 16px 0 30px 0;

  background-color: ${({ theme }) => theme.colors.primary_400_opacity};

  border-radius: 0 0 50px 50px;
`;

export const ListContainer = styled.div`
  padding: 32px 16px 16px 16px;

  display: flex;
  flex-direction: column;
  gap: 42px;
`;

export const ListTitle = styled.div`
  display: flex;
  justify-content: space-between;

  & > span {
    font-weight: 600;
  }
`;

export const FullListContainer = styled.div`
  padding: 32px 16px 16px 16px;

  display: flex;
  flex-direction: column;
  gap: 21px;
`;

export const FullListTitle = styled.div`
  display: flex;
  justify-content: space-between;

  & > span {
    font-weight: 600;
  }
`;

export const ExtraBold = styled.span`
  font-weight: 800;
`;

export const AdditionalExplanation = styled.div`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.primary_400};

  margin-top: 10px;
`;

export const ExtendedLink = (theme: Theme) => css`
  font-size: 12px;
  color: ${theme.colors.white_100};
  background-color: ${theme.colors.primary_300};
  padding: 3px 10px;

  border-radius: 20px;
`;

export const ExtendedButton = css`
  width: 200px;

  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;

  font-size: 16px;
  padding: 13px 45px 13px 20px;
`;
