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
      <Styled.SelectContainer tabIndex={0}>{children}</Styled.SelectContainer>
    </Styled.Root>
  );
};

SelectInput.VerticalView = function VerticalView(props: PropsWithChildren<SelectInputProps>) {
  const { label, children } = props;

  return (
    <Styled.Root>
      <label>{label}</label>
      <Styled.SelectVerticalContainer tabIndex={0}>{children}</Styled.SelectVerticalContainer>
    </Styled.Root>
  );
};

export default SelectInput;
