import java.util.List;
import java.util.ArrayList;

public class Main {

    public interface ISortingStrategy {
        void sort(List<Integer> list);
    }

    public static class BubbleSortStrategy implements ISortingStrategy {
        
        @Override
        public void sort(List<Integer> list) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size() - 1; j++) {
                    if (list.get(j) > list.get(j + 1)) {
                        int temp = list.get(j);
                        list.set(j, list.get(j + 1));
                        list.set(j + 1, temp);
                    }
                }
            }
        }
    }

    public static class MergeSortStrategy implements ISortingStrategy {
            
        @Override
        public void sort(List<Integer> list) {
            if (list.size() > 1) {
                List<Integer> left = new ArrayList<>();
                List<Integer> right = new ArrayList<>();
                
                int middle = list.size() / 2;
                
                for (int i = 0; i < middle; i++)
                    left.add(list.get(i));
                
                for (int i = middle; i < list.size(); i++)
                    right.add(list.get(i));

                sort(left);
                sort(right);

                merge(list, left, right);
            }
        }
    
        private void merge(List<Integer> list, List<Integer> left, List<Integer> right) {
            int leftIndex = 0;
            int rightIndex = 0;
            int listIndex = 0;
            
            while (leftIndex < left.size() && rightIndex < right.size()) {
                if (left.get(leftIndex) < right.get(rightIndex)) {
                    list.set(listIndex, left.get(leftIndex));
                    leftIndex++;
                } else {
                    list.set(listIndex, right.get(rightIndex));
                    rightIndex++;
                }
                listIndex++;
            }

            while (leftIndex < left.size()) {
                list.set(listIndex, left.get(leftIndex));
                leftIndex++;
                listIndex++;
            }
            
            while (rightIndex < right.size()) {
                list.set(listIndex, right.get(rightIndex));
                rightIndex++;
                listIndex++;
            }
        }
    }

    public static class SortingManager {
        private List<Integer> list;
        private ISortingStrategy strategy;

        public SortingManager(List<Integer> list, ISortingStrategy strategy) {
            this.list = list;
            this.strategy = strategy;
        }

        public List<Integer> sort() {
            this.strategy.sort(list);
            return list;
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        
        for(int i = 50;i >= 0;i -= 3) 
            list.add(i);
    
        SortingManager manager = new SortingManager(list, new BubbleSortStrategy());
        list = manager.sort();
        
        for (Integer i : list)
            System.out.println(i);
    }
}