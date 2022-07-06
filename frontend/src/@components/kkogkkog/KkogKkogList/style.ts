import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  & > div + div {
    margin-top: 20px;
  }
`;
