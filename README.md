# learn-mind
Mastermind player and learning algorithms by reinforcement learning.

#Current implementations and benchmark
This benchmark is done for a 6 color (or digits) master mind game.
The learning is constrained to use 16GB of ram and a max execution time of 1 hour. This is not very rigorous as the execution context is not strictly the same. The last column shows the number of episodes that was possible to fit within these constraints.

| **Algorithm**       | **Parameter** | **Rewards sum of playing 1M games** | **Episodes** |
|---------------------|:-------------:|:-----------------------------------:|:------------:|
| MC epsilon greedy   |      0.3      |               -690083               |      6M      |
| MC epsilon greedy   |      0.2      |               -286215               |      7M      |
| MC epsilon greedy   |      0.1      |                848485               |      12M     |
| MC epsilon greedy   |      0.05     |               1935992               |      22M     |
| MC epsilon greedy   |      0.01     |               2595339               |      90M     |
| MC epsilon greedy   |       0       |               -936267               |     150M     |
| MC exploring starts |       -       |               -908847               |      6M      | 

