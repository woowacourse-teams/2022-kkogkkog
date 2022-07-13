import { InputHTMLAttributes } from 'react';

import * as Styled from './style';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
  isShowLabel?: boolean;
  description?: string;
}

const Input = (props: InputProps) => {
  const { label, isShowLabel = true, description, id, type = 'text', ...rest } = props;

  return (
    <Styled.Root isShowLabel={isShowLabel}>
      <label htmlFor={id}>{label}</label>
      {description && <Styled.Description>{description}</Styled.Description>}
      <Styled.Input id={id} type={type} {...rest} />
    </Styled.Root>
  );
};

export default Input;
