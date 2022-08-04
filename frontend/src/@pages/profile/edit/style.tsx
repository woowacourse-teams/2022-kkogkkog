import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 24px;

  gap: 27px;
  padding-top: 27px;
`;

export const ImageInner = styled.div`
  text-align: center;

  & > img {
    border-radius: 50%;
  }
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 28px;
`;

export const ButtonInner = styled.div`
  text-align: center;
`;
