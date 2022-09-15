import { PropsWithChildren } from 'react';

import * as Styled from './style';

interface SelectInputProps {
  label: string;
}

const SelectInput = (props: PropsWithChildren<SelectInputProps>) => {
  const { label, children } = props;

  return (
    <Styled.Root>
      <label>{label}</label>
      <Styled.SelectContainer>{children}</Styled.SelectContainer>
    </Styled.Root>
  );
};

SelectInput.VerticalView = function VerticalView(props: PropsWithChildren<SelectInputProps>) {
  const { label, children } = props;

  return (
    <Styled.Root>
      <label>{label}</label>
      <Styled.SelectVerticalContainer>{children}</Styled.SelectVerticalContainer>
    </Styled.Root>
  );
};

export default SelectInput;
