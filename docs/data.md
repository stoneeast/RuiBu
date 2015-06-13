## Data Denormalization

Majorly there is just one fact table `transaction.txt`, and one dimension table `stkmas.txt`. The
transaction table have all the inventory movements, and the stock master is a product master file.

All other files are lookup tables.

The whole er-diagram
![er-diagram](https://rawgit.com/ninjapapa/RuiBu/master/docs/erdiagram/er.svg)

We simply joined all the lookup tables to those 2 main table for de-normalization. After this step the
er-diagram is
![denorm er-diagram](https://rawgit.com/ninjapapa/RuiBu/master/docs/erdiagram/denormed_er.svg)