import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;

  background-color: ${({ theme }) => theme.colors.background_3};
`;

export const Container = styled.div`
  max-width: 414px;
  min-width: 320px;

  margin: 0 auto;

  position: relative;

  box-shadow: 0 4px 4px 0 #00000025;
`;
