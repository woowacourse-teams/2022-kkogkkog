const CustomSuspense = (props: any) => {
  const { fallback, isLoading, children } = props;

  if (isLoading) {
    return fallback;
  }

  return children;
};

export default CustomSuspense;
