class Solution {
    public int swimInWater(int[][] grid) {
        int n=grid.length,m=grid[0].length,i,j;
        boolean[][] visited=new boolean[n][m];
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->Integer.compare(a[0],b[0]));
        int[][] dir={{0,-1},{-1,0},{0,1},{1,0}};
        pq.add(new int[]{grid[0][0],0,0});
        int max=grid[0][0];
        while(!pq.isEmpty())
        {
            int[] x=pq.poll();
            int val=x[0];
            int r=x[1];
            int c=x[2];
            if(r==n-1 && c==m-1)
                return Math.max(max,val);
            if(visited[r][c])
                continue;
            visited[r][c]=true;
            max=Math.max(max,val);
            for(int[] d:dir)
            {
                int er=r+d[0];
                int ec=c+d[1];
                if(er>=0 && er<n && ec>=0 && ec<m && !visited[er][ec])
                {
                    pq.add(new int[]{grid[er][ec],er,ec});
                }
            }
        }
        return 0;
    }
}
