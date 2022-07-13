import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;

  & > label {
    display: inline-block;

    color: ${({ theme }) => theme.colors.drak_grey_200};
    font-size: 14px;

    margin-bottom: 8px;
  }

  & > input {
    border-radius: 4px;
    padding: 10px;
    border: 1px solid ${({ theme }) => theme.colors.light_grey_100};
  }

  box-shadow: 0 1px 1px 0 #00000025;
`;
