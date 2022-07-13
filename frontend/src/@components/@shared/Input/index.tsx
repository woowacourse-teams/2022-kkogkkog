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
      <input id={id} type={type} {...rest} />
    </Styled.Root>
  );
};

export default Input;
