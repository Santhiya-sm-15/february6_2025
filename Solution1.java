class Solution {
    public int tupleSameProduct(int[] nums) {
        Map<Long,Integer> m=new HashMap<>();
        int i,j;
        for(i=0;i<nums.length;i++)
        {
            for(j=i+1;j<nums.length;j++)
            {
                long pro=nums[i]*nums[j];
                m.put(pro,m.getOrDefault(pro,0)+1);
            }
        }
        int cnt=0;
        for(Map.Entry<Long,Integer> x:m.entrySet())
        {
            int a=x.getValue();
            a=4*(a*(a-1));
            cnt+=(a);
        }
        return cnt;
    }
}