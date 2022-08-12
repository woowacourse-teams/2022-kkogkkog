import { ChangeEventHandler, Dispatch, SetStateAction, useState } from 'react';

type Validation = (value: string) => boolean;

const useInput = (
  defaultValue: string,
  validations?: Validation[]
): [
  string,
  ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement>,
  Dispatch<SetStateAction<string>>
] => {
  const [value, setValue] = useState(defaultValue);

  const onChange: ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = e => {
    const {
      target: { value },
    } = e;

    if (validations?.some(validation => validation(value))) {
      return;
    }
    setValue(value);
  };

  return [value, onChange, setValue];
};

export default useInput;
