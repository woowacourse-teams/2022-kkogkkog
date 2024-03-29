import type { Theme } from '@emotion/react';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  background-color: ${({ theme }) => theme.colors.white_100};
  border-radius: 4px;
`;

export const CreateCouponContainer = styled.section`
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

export const ExtraBold = styled.h1`
  font-weight: 700;
  display: inline-block;
`;

export const AdditionalExplanation = styled.p`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.primary_400};

  margin-top: 10px;
`;

export const FullListContainer = styled.section`
  padding: 32px 16px 16px 16px;

  display: flex;
  flex-direction: column;
  gap: 21px;
`;

export const FullListTitle = styled.div`
  display: flex;
  justify-content: space-between;

  & > h3 {
    font-weight: 600;
  }
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

  & > h2 {
    font-weight: 600;
  }
`;

export const UnRegisteredCouponSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

export const UnRegisteredCouponSectionInner = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
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

export const ExtendedUnRegisteredCouponMoreButton = css`
  width: 200px;

  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;

  font-size: 16px;
  padding: 13px 0;
`;
