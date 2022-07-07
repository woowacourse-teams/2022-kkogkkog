import { InputHTMLAttributes } from 'react';

import * as Styled from './style';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
}

const Input = ({ label, ...props }: InputProps) => {
  return (
    <Styled.Root>
      <label>{label}</label>
      <input type='text' {...props} />
    </Styled.Root>
  );
};

export default Input;
