import styled from '@emotion/styled';

export const Root = styled.div`
  & > label {
    display: inline-block;

    color: ${({ theme }) => theme.colors.dark_grey_200};
    font-weight: 600;
    font-size: 14px;

    margin-bottom: 8px;
  }
`;

export const SelectContainer = styled.ul`
  overflow-x: scroll;

  padding: 10px;

  display: flex;
  align-items: center;
  gap: 10px;

  & > li {
    cursor: pointer;
  }

  & > li:hover {
    opacity: 0.99;

    transition: opacity 0.2s ease-in;
  }
`;

export const SelectVerticalContainer = styled.ul`
  padding: 10px;

  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;

  & > li {
    cursor: pointer;

    min-width: 80px;
    text-align: center;
  }

  & > li:hover {
    opacity: 0.99;

    transition: opacity 0.2s ease-in;
  }
`;
