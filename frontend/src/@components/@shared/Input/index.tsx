import { InputHTMLAttributes } from 'react';

import * as Styled from './style';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
  description?: string;
}

const Input = ({ label, description, id, type = 'text', ...props }: InputProps) => {
  return (
    <Styled.Root>
      <label htmlFor={id}>{label}</label>
      {description && <Styled.Description>{description}</Styled.Description>}
      <input id={id} type={type} {...props} />
    </Styled.Root>
  );
};

export default Input;
