import h5py
import glob
from pprint import pprint
import numpy as np

all_files = glob.glob(r"/g/kreshuk/yu/Outputs/VBondarenko2021Embryo/spoco_adrian/New Folder/*")
pprint(all_files)

for file in all_files:
    file_out = file[:-3] + "_converted.h5"
    with h5py.File(file, 'r') as f_in, h5py.File(file_out, 'w') as f_out:
        for name in f_in.keys():
            f_out.create_dataset(name, data=f_in[name][:].astype(np.float32), compression='gzip')
