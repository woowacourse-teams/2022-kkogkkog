import { InputHTMLAttributes } from 'react';

import * as Styled from './style';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
  description?: string;
}

const Input = (props: InputProps) => {
  const { label, description, id, type = 'text', ...rest } = props;

  return (
    <Styled.Root>
      <label htmlFor={id}>{label}</label>
      {description && <Styled.Description>{description}</Styled.Description>}
      <Styled.Input id={id} type={type} {...rest} />
    </Styled.Root>
  );
};

Input.HiddenLabel = function HiddenLabel(props: InputProps) {
  const { label, id, type = 'text', ...rest } = props;

  return (
    <Styled.Root isShowLabel={false}>
      <label htmlFor={id}>{label}</label>
      <Styled.Input id={id} type={type} {...rest} />
    </Styled.Root>
  );
};

export default Input;
