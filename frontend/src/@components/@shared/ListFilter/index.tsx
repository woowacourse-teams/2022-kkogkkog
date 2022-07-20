import * as Styled from './style';

interface ListFilterProps<T> {
  status: T;
  options: readonly T[];
  onClickFilterButton: (status: T) => void;
}

const ListFilter = <T extends string>(props: ListFilterProps<T>) => {
  const { status, options, onClickFilterButton } = props;

  return (
    <Styled.Root>
      {options.map(option => (
        <Styled.FilterButton
          key={option}
          isFocus={status === option}
          onClick={() => onClickFilterButton(option)}
        >
          {option}
        </Styled.FilterButton>
      ))}
    </Styled.Root>
  );
};

export default ListFilter;
