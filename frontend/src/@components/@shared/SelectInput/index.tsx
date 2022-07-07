import * as Styled from './style';

const SelectInput = ({ label, children }) => {
  return (
    <Styled.Root>
      <label>{label}</label>
      <Styled.SelectContainer>{children}</Styled.SelectContainer>
    </Styled.Root>
  );
};

export default SelectInput;
