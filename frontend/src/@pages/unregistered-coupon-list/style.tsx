import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  border-radius: 4px;

  padding: 20px 0;
`;

export const ListFilterContainer = styled.div`
  padding: 0 50px;
`;

export const Container = styled.section`
  margin-bottom: 10px;
`;

export const LinkInner = styled.div`
  position: sticky;
  bottom: 0;
  right: 0;

  display: flex;
  justify-content: flex-end;
`;

export const ExtendedLink = css`
  position: relative;
  right: 16px;
  bottom: 16px;
`;
