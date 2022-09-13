import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 20px;

  height: 100%;
  cursor: pointer;

  color: ${({ theme }) => theme.colors.light_grey_200};
`;
