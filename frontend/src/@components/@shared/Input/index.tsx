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

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  value: number;
  label: string;
  description?: string;
  onChangeCouponCount: any;
}

Input.Counter = function CounterInput(props: InputProps) {
  const { label, id, value, onChangeCouponCount, ...rest } = props;

  const onClickMinus = () => {
    onChangeCouponCount(value - 1);
  };

  const onClickPlus = () => {
    onChangeCouponCount(value + 1);
  };

  return (
    <Styled.Root>
      <label htmlFor={id}>{label}</label>
      <Styled.CounterInputContainer>
        <Styled.MinusCounterButton onClick={onClickMinus}>-</Styled.MinusCounterButton>
        <Styled.Input id={id} type='number' value={value} {...rest} disabled />
        <Styled.PlusCounterButton onClick={onClickPlus}>+</Styled.PlusCounterButton>
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
