import { InputHTMLAttributes, MouseEventHandler } from 'react';

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

interface CounterInputProps extends InputHTMLAttributes<HTMLInputElement> {
  value: number;
  label: string;
  description?: string;
  onClickPlusButton: MouseEventHandler<HTMLButtonElement>;
  onClickMinusButton: MouseEventHandler<HTMLButtonElement>;
}

Input.Counter = function CounterInput(props: CounterInputProps) {
  const { label, id, description, value, onClickMinusButton, onClickPlusButton, ...rest } = props;

  return (
    <Styled.Root>
      <label htmlFor={id}>{label}</label>
      {description && <Styled.Description>{description}</Styled.Description>}
      <Styled.CounterInputContainer>
        <Styled.MinusCounterButton type='button' onClick={onClickMinusButton}>
          -
        </Styled.MinusCounterButton>
        <Styled.Input id={id} type='number' value={value} disabled {...rest} />
        <Styled.PlusCounterButton type='button' onClick={onClickPlusButton}>
          +
        </Styled.PlusCounterButton>
      </Styled.CounterInputContainer>
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
