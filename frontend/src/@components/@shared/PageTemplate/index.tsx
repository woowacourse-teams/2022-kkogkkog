import * as Styled from './style';

const PageTemplate = ({ children }) => {
  return (
    <Styled.Root>
      <Styled.Container>{children}</Styled.Container>
    </Styled.Root>
  );
};

export default PageTemplate;
