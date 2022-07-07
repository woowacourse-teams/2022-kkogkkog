import styled from '@emotion/styled';

export const Root = styled.div`
  & > label {
    display: inline-block;

    color: ${({ theme }) => theme.colors.drak_grey_200};
    font-size: 14px;

    margin-bottom: 8px;
  }
`;

export const SelectContainer = styled.ul`
  overflow-x: scroll;

  padding: 10px;

  display: flex;
  align-items: center;

  border: 1px solid ${({ theme }) => theme.colors.light_grey_100};
  border-radius: 4px;

  & > li + li {
    margin-left: 10px;
  }

  & > li {
    cursor: pointer;
  }
`;
